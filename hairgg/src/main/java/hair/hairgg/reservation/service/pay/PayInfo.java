package hair.hairgg.reservation.service.pay;

import java.time.LocalDateTime;

import lombok.Getter;

public class PayInfo {
	public record PayReadyInfo(String tid, String next_redirect_pc_url) {
	}

	public record PayApproveInfo(LocalDateTime approved_at) {
	}
}
