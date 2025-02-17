package hair.hairgg.reservation.service.pay;

import hair.hairgg.reservation.ReservationResDto;
import hair.hairgg.reservation.domain.Reservation;

public interface PayService {
	public PayInfo.PayReadyInfo payReady(Reservation reservation);

	public PayInfo.PayApproveInfo payApprove(Reservation reservation, String pg_token);
}
