package day13;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListPacket extends Packet {
    private List<Packet> packetList = new ArrayList<>();
}
