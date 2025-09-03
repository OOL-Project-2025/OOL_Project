package com.OOL.oolfinance.entity.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : Chat
 * @date : 2025. 5. 8. / 오후 8:13
 * @modifyed :
 **/

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Column
    private String nickname;

    @Column
    private String message;

    @Column
    private LocalDateTime time;
}


