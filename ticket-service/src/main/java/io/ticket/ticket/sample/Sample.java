package io.ticket.ticket.sample;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sample {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;
}
