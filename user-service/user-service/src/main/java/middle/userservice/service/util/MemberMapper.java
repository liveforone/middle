package middle.userservice.service.util;

import middle.userservice.domain.Member;
import middle.userservice.dto.response.MemberResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MemberMapper {

    public static MemberResponse entityToDto(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .realName(member.getRealName())
                .auth(member.getAuth())
                .build();
    }

    public static List<MemberResponse> entityToDtoList(List<Member> members) {
        return members
                .stream()
                .map(MemberMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
