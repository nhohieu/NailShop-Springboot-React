package vn.numdum.NailShop.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import vn.numdum.NailShop.util.Constant.GenderEnum;

@Getter
@Setter
public class RestUpdateUserDTO {
    private long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
}
