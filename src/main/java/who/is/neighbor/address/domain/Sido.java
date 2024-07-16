package who.is.neighbor.address.domain;

import who.is.neighbor.address.infrastructure.entity.SidoEntity;

public record Sido(String sidoName) {
    public static Sido from(SidoEntity sido) {
        return new Sido(sido.getSidoName());
    }
}
