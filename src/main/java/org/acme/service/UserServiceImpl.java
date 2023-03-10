package org.acme.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import org.acme.dto.request.AuthRequest;
import org.acme.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.Email;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private ReactiveMailer mailer;

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
        if (user.isEmpty()) throw new NotFoundException("User not empty");

        return user.get();
    }

    @Override
    public User update(User obj, Long id) {
        Optional<User> userOpt = User.findByIdOptional(id);
        if (userOpt.isEmpty()) throw new NotFoundException("User not found");

        // não funciona
        Optional<User> userByEmail = User.findByEmail(obj.email);
        if (userByEmail.isPresent() && userByEmail.get().id != obj.id) {
            throw new BadRequestException("Este email já existe");
        }

        User user = userOpt.get();
        user.userName = obj.userName;
        user.email = obj.email;
        user.tel = obj.tel;
        user.password = obj.password;
        user.updatedAt = LocalDateTime.now();
        user.birthDate = obj.birthDate;
        user.perfilImage = obj.perfilImage;
        return user;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = User.findByIdOptional(id);
        if (user.isEmpty()) throw new NotFoundException("User not empty");

        user.get().delete();
    }

    public String login(AuthRequest auth) {
        Optional<User> user = User.findByEmail(auth.getEmail());
        if (user.isPresent() && BcryptUtil.matches(auth.getPassword(), user.get().password)) {
            return "Usuário logado com sucesso";
        }
        throw new BadRequestException("invalid credentials");
    }

    public CompletionStage resetPassword(String email) {
        //gerar senha aleátorio
        String newPassword = generateRandomPassword(12);

        User user = User.findByEmail(email).orElseThrow(() -> new BadRequestException());
        user.password = BcryptUtil.bcryptHash(newPassword);
        user.persist();

        return mailer.send(
                Mail.withText(email,
                        "Esqueci minha senha",
                        "A sua sua nova senha é: " + user.password
                )
        ).subscribeAsCompletionStage().thenApply(x -> Response.accepted().build());
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
