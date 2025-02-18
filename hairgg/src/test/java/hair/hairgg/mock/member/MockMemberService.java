package hair.hairgg.mock.member;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberRepository;
import hair.hairgg.member.MemberService;
import hair.hairgg.security.jwt.JwtUtil;

public class MockMemberService extends MemberService {
	public MockMemberService(MemberRepository memberRepository,
		JwtUtil jwtUtil) {
		super(memberRepository, jwtUtil);
	}

	public MockMemberService() {
		super(null, null);
	}

	@Override
	public Member findById(Long id) {
		if (id == 1) {
			return new Member();
		}
		throw new GeneralException(ErrorCode.MEMBER_NOT_FOUND);
	}


}
