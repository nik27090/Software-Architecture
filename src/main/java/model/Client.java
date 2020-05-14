package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Data
public class Client extends BasePerson {
    @Column(name = "bankAccount")
    private String bankAccount;
}
