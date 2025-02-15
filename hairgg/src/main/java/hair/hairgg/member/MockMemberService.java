package hair.hairgg.member;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MockMemberService implements MemberService{


	private final MemberRepository memberRepository;

	@Override
	public Member findById(Long id) {
		return memberRepository.findById(id).orElse(null);
	}
}
