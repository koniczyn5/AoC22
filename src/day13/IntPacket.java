package day13;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IntPacket extends Packet {
    private final int value;
}
