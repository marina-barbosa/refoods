package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
