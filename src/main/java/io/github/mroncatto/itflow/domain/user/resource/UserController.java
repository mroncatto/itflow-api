package io.github.mroncatto.itflow.domain.user.resource;

import io.github.mroncatto.itflow.domain.user.abstracts.AbstractUserController;
import io.github.mroncatto.itflow.domain.user.service.UserService;

public class UserController extends AbstractUserController {

    public UserController(UserService userService) {
        super(userService);
    }
}
