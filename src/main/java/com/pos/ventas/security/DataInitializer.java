package com.pos.ventas.security;

import com.pos.ventas.model.Usuario;
import com.pos.ventas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public DataInitializer(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (repo.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRol("ADMIN");
            repo.save(admin);
        }

        if (repo.findByUsername("cajero").isEmpty()) {
            Usuario cajero = new Usuario();
            cajero.setUsername("cajero");
            cajero.setPassword(encoder.encode("cajero123"));
            cajero.setRol("CAJERO");
            repo.save(cajero);
        }
    }
}
