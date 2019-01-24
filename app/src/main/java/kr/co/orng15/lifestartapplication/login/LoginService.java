package kr.co.orng15.lifestartapplication.login;

import kr.co.orng15.lifestartapplication.data.Company_Member;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginService {
    static private LoginService curr = null;
    private String loginId;
    private String loginPw;
    private Member loginMember;

    public static LoginService getInstance() {
        if (curr == null) {
            curr = new LoginService();
        }
        return curr;
    }
}
