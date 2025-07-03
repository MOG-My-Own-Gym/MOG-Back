package com.project.mog.repository.bios;

import java.time.LocalDateTime;

import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="bios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiosEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_BIOS_GENERATOR",sequenceName = "SEQ_BIOS",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_BIOS_GENERATOR" )	
	private long bioId;
	@Column(length=3,nullable=false)
	private long age;
	@Column(nullable=false)
	private boolean gender;
	@Column(length=3,nullable=false)
	private long height;
	@Column(length=3,nullable=false)
	private long weight;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usersId", referencedColumnName = "usersId", nullable = true)
	private UsersEntity user;
    
}
