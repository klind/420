package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.AnswerDTO;
import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.dto.entity.QuestionDTO;
import com.mmj.data.core.dto.entity.QuestionRangeDTO;
import com.mmj.data.core.dto.entity.SurveyDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.util.JsonUtils;
import com.mmj.data.ejb.session.AnswerSB;
import com.mmj.data.ejb.session.ProfileSB;
import com.mmj.data.ejb.session.QuestionRangesSB;
import com.mmj.data.ejb.session.QuestionSB;
import com.mmj.data.ejb.session.SurveySB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MMJRestWebservice implements MMJRestWebserviceI {
    private static final Logger LOG = LoggerFactory.getLogger(MMJRestWebservice.class);

    @Context
    private SecurityContext securityContext;
    ;

    @Inject
    private ProfileSB profileSB;

    @Inject
    private SurveySB surveySB;

    @Inject
    private QuestionRangesSB questionRangesSB;

    @Inject
    private QuestionSB questionSB;

    @Inject
    private AnswerSB answerSB;

    @Override
    public Response createProfile(ProfileDTO profileDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Saving profile {}.", profileDTO);
        ProfileDTO result = profileSB.createProfile(profileDTO);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getProfileById(Long id, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Looking up profile with id {}", id);
        ProfileDTO result = profileSB.getProfileById(id);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getProfileByEmail(String email, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Looking up profile with email {}", email);
        ProfileDTO result = profileSB.getProfileByEmail(email);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getProfiles(HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Getting profiles");
        List<ProfileDTO> result = profileSB.getProfiles();
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response createSurvey(SurveyDTO surveyDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Saving survey {}.", surveyDTO);
        SurveyDTO result = surveySB.createSurvey(surveyDTO);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSurveyById(Long id, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Looking up survey with id {}", id);
        SurveyDTO result = surveySB.getSurveyById(id);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getMySurveys(HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Looking up surveys");
        Long userID = Long.parseLong(servletRequest.getHeader("userID"));
        List<SurveyDTO> result = surveySB.getMySurveys(userID);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSurveys(HttpServletRequest servletRequest) {
        LOG.info("Looking up surveys");
        List<SurveyDTO> result = surveySB.getSurveys();
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getAnswersBySurveyId(Long surveyId, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Looking up answers by survey id", surveyId);
        List<AnswerDTO> result = answerSB.getAnswersBySurveyId(surveyId);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getQuestionsBySurveyId(Long surveyId, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Getting all questions.");
        List<QuestionDTO> result = questionSB.getQuestionsBySurveyId(surveyId);
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getQuestions(HttpServletRequest servletRequest) {
        LOG.info("Getting all questions.");
        List<QuestionDTO> result = questionSB.getQuestions();
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getQuestionRanges(HttpServletRequest servletRequest) {
        LOG.info("Getting all scores ranges.");
        List<QuestionRangeDTO> result = questionRangesSB.getQuestionRanges();
        LOG.debug("Result : {}", JsonUtils.serialize(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response createAnswer(AnswerDTO answerDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Saving answer {}.", answerDTO);
        AnswerDTO answer = answerSB.createAnswer(answerDTO);
        LOG.debug("Saved answer");
        return Response.status(Response.Status.OK).entity(answer).build();
    }

   /* @Override
    public Response createAnswers(AnswerListWrapper answerListWrapper, HttpServletRequest servletRequest) throws BusinessException {
        List<AnswerDTO> result = new ArrayList<>();
        for (AnswerDTO answerDTO : answerListWrapper.getAnswers()) {
            result.add(answerSB.createAnswer(answerDTO));
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }*/
}
