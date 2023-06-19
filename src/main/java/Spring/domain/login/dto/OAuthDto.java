package Spring.domain.login.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthDto {

    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthDto(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuthDto of(SocialType socialType, String userNameAttributeName, Map<String, Object> attribute) {
        if (socialType == SocialType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attribute);
        }
    }

    public static OAuthDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthDto.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }
}
