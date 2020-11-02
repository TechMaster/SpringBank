package com.xbank.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "authority")
public class Authority {

   @Id
   @Column(name = "name", length = 50)
   @NotNull
   private String name;

   @ManyToMany(mappedBy = "authorities")
   private Collection<User> users;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Collection<User> getUsers() {
      return users;
   }

   public void setUsers(Collection<User> users) {
      this.users = users;
   }
}
