package middle.userservice.service.util;

import middle.userservice.domain.Member;
import middle.userservice.dto.response.MemberResponse;
import middle.userservice.utility.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberMapper {

    public static MemberResponse entityToDto(Member member) {
        if (CommonUtils.isNull(member)) {
            return new MemberResponse();
        }

        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .realName(member.getRealName())
                .auth(member.getAuth())
                .build();
    }

    public static List<MemberResponse> entityToDtoList(List<Member> members) {
        if (CommonUtils.isNull(members)) {
            return new ArrayList<>();
        }

        return members
                .stream()
                .map(MemberMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
