package com.mmj.data.ejb.session;

import com.lambdaworks.crypto.SCryptUtil;
import com.mmj.data.core.LoginDTO;
import com.mmj.data.core.ProfilePasswordDTO;
import com.mmj.data.core.TokenDTO;
import com.mmj.data.core.dto.entity.RegisterDTO;
import com.mmj.data.core.exception.AlreadyExistsException;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotAuthorizedException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.util.SessionIdentifierGenerator;
import com.mmj.data.core.util.TokenStorage;
import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.model.ProfileEN;
import com.mmj.data.ejb.model.ProfilePasswordEN;
import com.mmj.data.ejb.transformer.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@LocalBean
public class SecuritySB {
    private static final Logger LOG = LoggerFactory.getLogger(SecuritySB.class);

    @Inject
    private ProfileDao profileDao;

    @Inject
    private Transformer transformer;

    @Inject
    private SessionIdentifierGenerator sessionIdentifierGenerator;

    public TokenDTO register(RegisterDTO registerDTO) throws BusinessException {

        if(profileDao.profileExistsByEmail(registerDTO.getEmail())){
            throw new AlreadyExistsException("The email " + registerDTO.getEmail() + " is already in use");
        }


        TokenDTO tokenDTO = null;
        String password1 = registerDTO.getPassword1();
        String password2 = registerDTO.getPassword2();

        if (!password1.equals(password2)) {
            throw new BusinessException("Passwords do not match");
        }

        String generatedSecuredPasswordHash = SCryptUtil.scrypt(password1, 16, 16, 16);
        ProfilePasswordEN profilePasswordEN = new ProfilePasswordEN();
        profilePasswordEN.setEmail(registerDTO.getEmail());
        profilePasswordEN.setPassword(generatedSecuredPasswordHash);
        profilePasswordEN.setRole("user");
        profileDao.saveNewProfilePassword(profilePasswordEN);

        String token = sessionIdentifierGenerator.nextSessionId();
        TokenStorage.addToken(token);
        tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return tokenDTO;
    }

    public String getProfilePassword(String email) throws NotFoundException {
        String hashedPassword = profileDao.getProfilePassword(email);
        return hashedPassword;
    }

    public ProfilePasswordDTO getProfilPasswordByEmail(String email) throws NotFoundException {
        ProfilePasswordEN profilePasswordEN = profileDao.getProfilePasswordeByEmail(email);
        return transformer.getProfilePasswordDTO(profilePasswordEN);
    }

    public TokenDTO login(LoginDTO loginDTO) throws NotAuthorizedException {
        TokenDTO tokenDTO = null;
        if (loginDTO == null || StringUtils.isBlank(loginDTO.getEmail()) || StringUtils.isBlank(loginDTO.getPassword())) {
            throw new NotAuthorizedException("");
        } else {
            String email = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            ProfilePasswordDTO profilePasswordDTO = null;
            try {
                profilePasswordDTO = getProfilPasswordByEmail(email);
            } catch (NotFoundException e) {
                throw new NotAuthorizedException("");
            }
            if (SCryptUtil.check(password, profilePasswordDTO.getHashedPassword())) {
                String token = sessionIdentifierGenerator.nextSessionId();
                TokenStorage.addToken(token);
                tokenDTO = new TokenDTO();
                tokenDTO.setToken(token);
                ProfileEN profile = null;
                try {
                    profile = profileDao.getProfileByEmail(email);
                    tokenDTO.setId(profile.getId());
                } catch (NotFoundException e) {

                }
            } else {
                throw new NotAuthorizedException("");
            }
        }
        return tokenDTO;
    }
}
