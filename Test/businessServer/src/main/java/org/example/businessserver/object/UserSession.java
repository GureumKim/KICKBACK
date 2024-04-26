package org.example.businessserver.object;

import reactor.netty.Connection;

/**
 * 사용자 세션 정보를 표현하는 불변의 레코드입니다.
 *
 * Java 14 이상에서 도입된 레코드(record)는 데이터 운반 목적의 클래스를 간결하게 작성할 수 있도록 도와줍니다.
 * 이 레코드는 사용자의 ID와 해당 사용자의 연결 정보를 저장합니다.
 *
 * @param userName 사용자의 고유 식별자입니다. 이를 통해 사용자를 구별할 수 있습니다.
 * @param connection 사용자의 연결 정보입니다. 이 정보를 통해 사용자와의 통신을 관리할 수 있습니다.
 */
public record UserSession(String userName, Connection connection) {
}