package com.digi.model.resource;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tymoshenkol on 26-Aug-16.
 */
@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class IdEntity implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
}
