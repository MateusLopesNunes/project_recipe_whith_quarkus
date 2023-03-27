package org.acme.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import org.acme.dto.request.AuthRequest;
import org.acme.models.User;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private ReactiveMailer mailer;

    @Inject
    private ImageService imageService;

    @Override
    public List<User> getAll() {
        return User.listAll();
    }

    @Override
    public User getByName(String name) {
        return User.findByName(name);
    }

    @Override
    public User create(User user) {
        Optional<User> byEmail = User.findByEmail(user.email);
        if (byEmail.isPresent()) throw new BadRequestException("Este email já existe");

        user.role = "user";
        user.password = BcryptUtil.bcryptHash(user.password);
        user.persist();
        return user;
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = User.findByIdOptional(id);
        if (user.isEmpty()) throw new NotFoundException("User not found");

        return user.get();
    }

    @Override
    public User update(User obj, Long id) {
        Optional<User> userOpt = User.findByIdOptional(id);
        if (userOpt.isEmpty()) throw new NotFoundException("User not found");

        Optional<User> userByEmail = User.findByEmail(obj.email);
        if (userByEmail.isPresent() && !userByEmail.get().id.equals(obj.id)) {
            throw new BadRequestException("Este email já existe");
        }

        User user = userOpt.get();
        user.userName = obj.userName;
        user.email = obj.email;
        user.tel = obj.tel;
        user.password = obj.password;
        user.updatedAt = LocalDateTime.now();
        user.birthDate = obj.birthDate;

        return user;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = User.findByIdOptional(id);
        if (user.isEmpty()) throw new NotFoundException("User not found");

        user.get().delete();
    }

    @Override
    public String login(AuthRequest auth) {
        Optional<User> user = User.findByEmail(auth.getEmail());
        if (user.isPresent() && BcryptUtil.matches(auth.getPassword(), user.get().password)) {
            return "Usuário logado com sucesso";
        }
        throw new BadRequestException("invalid credentials");
    }

    @Override
    public CompletionStage resetPassword(String email) {
        //gerar senha aleátoria
        String newPassword = generateRandomPassword(12);

        User user = User.findByEmail(email).orElseThrow(() -> new BadRequestException("Email inválido"));
        user.password = BcryptUtil.bcryptHash(newPassword);
        user.persist();

        return mailer.send(
                Mail.withText(email,
                        "Esqueci minha senha",
                        "A sua sua nova senha é: " + newPassword
                )
        ).subscribeAsCompletionStage().thenApply(x -> Response.accepted().build());
    }

    @Override
    public User addPerfilImage(MultipartFormDataInput input, Long id) {
        Optional<User> userOpt = User.findByIdOptional(id);
        if (userOpt.isEmpty()) throw new NotFoundException("User not found");

        User user = userOpt.get();
        user.perfilImage = imageService.uploadFile(input, "/upload/images")
                                       .stream().findFirst().get();

        return user;
    }

    //Utill
    private String generateRandomPassword(int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
