package hair.hairgg.mock.member;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberService;

public class MockMemberService implements MemberService {
	@Override
	public Member findById(Long id) {
		if (id == 1) {
			return new Member();
		}
		throw new GeneralException(ErrorCode.MEMBER_NOT_FOUND);
	}
}
