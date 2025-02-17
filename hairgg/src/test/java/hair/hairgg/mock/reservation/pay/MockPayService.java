package hair.hairgg.mock.reservation.pay;

import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.service.pay.PayInfo;
import hair.hairgg.reservation.service.pay.PayService;

public class MockPayService implements PayService {
	@Override
	public PayInfo.PayReadyInfo payReady(Reservation reservation) {
		return null;
	}

	@Override
	public PayInfo.PayApproveInfo payApprove(Reservation reservation, String pg_token) {
		return null;
	}
}
