package org.acme.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.dto.request.AuthRequest;
import org.acme.models.Category;
import org.acme.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

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
        if (byEmail.isPresent()) {
            throw new BadRequestException("Este email já existe");
        }

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
        if (userOpt.isEmpty()) throw new NotFoundException("User not empty");

        //não ta funcionando
//        Optional<User> byEmail = User.findByEmail(obj.email);
//        if (!byEmail.get().id.equals(obj.id)) {
//            throw new BadRequestException("Este email já existe");
//        }

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
        return "Credenciais invalidas";
    }
}
