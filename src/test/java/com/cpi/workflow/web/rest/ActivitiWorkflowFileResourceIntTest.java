package com.cpi.workflow.web.rest;

import com.cpi.workflow.CpiworkflowApp;

import com.cpi.workflow.config.SecurityBeanOverrideConfiguration;

import com.cpi.workflow.domain.ActivitiWorkflowFile;
import com.cpi.workflow.repository.ActivitiWorkflowFileRepository;
import com.cpi.workflow.service.ActivitiWorkflowFileService;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.service.mapper.ActivitiWorkflowFileMapper;
import com.cpi.workflow.web.rest.errors.ExceptionTranslator;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileCriteria;
import com.cpi.workflow.service.ActivitiWorkflowFileQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cpi.workflow.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivitiWorkflowFileResource REST controller.
 *
 * @see ActivitiWorkflowFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpiworkflowApp.class, SecurityBeanOverrideConfiguration.class})
public class ActivitiWorkflowFileResourceIntTest {

    private static final String DEFAULT_WORKFLOW_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKFLOW_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROCESS_DEFINITION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROCESS_DEFINITION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROCESS_DEFINITION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROCESS_DEFINITION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PROCESS_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROCESS_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROCESS_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROCESS_IMAGE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_UPLOAD_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPLOAD_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_USER = "BBBBBBBBBB";

    @Autowired
    private ActivitiWorkflowFileRepository activitiWorkflowFileRepository;

    @Autowired
    private ActivitiWorkflowFileMapper activitiWorkflowFileMapper;

    @Autowired
    private ActivitiWorkflowFileService activitiWorkflowFileService;

    @Autowired
    private ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivitiWorkflowFileMockMvc;

    private ActivitiWorkflowFile activitiWorkflowFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivitiWorkflowFileResource activitiWorkflowFileResource = new ActivitiWorkflowFileResource(activitiWorkflowFileService, activitiWorkflowFileQueryService);
        this.restActivitiWorkflowFileMockMvc = MockMvcBuilders.standaloneSetup(activitiWorkflowFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivitiWorkflowFile createEntity(EntityManager em) {
        ActivitiWorkflowFile activitiWorkflowFile = new ActivitiWorkflowFile()
            .workflowName(DEFAULT_WORKFLOW_NAME)
            .processDefinition(DEFAULT_PROCESS_DEFINITION)
            .processDefinitionContentType(DEFAULT_PROCESS_DEFINITION_CONTENT_TYPE)
            .processImage(DEFAULT_PROCESS_IMAGE)
            .processImageContentType(DEFAULT_PROCESS_IMAGE_CONTENT_TYPE)
            .uploadTime(DEFAULT_UPLOAD_TIME)
            .uploadUser(DEFAULT_UPLOAD_USER);
        return activitiWorkflowFile;
    }

    @Before
    public void initTest() {
        activitiWorkflowFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivitiWorkflowFile() throws Exception {
        int databaseSizeBeforeCreate = activitiWorkflowFileRepository.findAll().size();

        // Create the ActivitiWorkflowFile
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO = activitiWorkflowFileMapper.toDto(activitiWorkflowFile);
        restActivitiWorkflowFileMockMvc.perform(post("/api/activiti-workflow-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiWorkflowFileDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivitiWorkflowFile in the database
        List<ActivitiWorkflowFile> activitiWorkflowFileList = activitiWorkflowFileRepository.findAll();
        assertThat(activitiWorkflowFileList).hasSize(databaseSizeBeforeCreate + 1);
        ActivitiWorkflowFile testActivitiWorkflowFile = activitiWorkflowFileList.get(activitiWorkflowFileList.size() - 1);
        assertThat(testActivitiWorkflowFile.getWorkflowName()).isEqualTo(DEFAULT_WORKFLOW_NAME);
        assertThat(testActivitiWorkflowFile.getProcessDefinition()).isEqualTo(DEFAULT_PROCESS_DEFINITION);
        assertThat(testActivitiWorkflowFile.getProcessDefinitionContentType()).isEqualTo(DEFAULT_PROCESS_DEFINITION_CONTENT_TYPE);
        assertThat(testActivitiWorkflowFile.getProcessImage()).isEqualTo(DEFAULT_PROCESS_IMAGE);
        assertThat(testActivitiWorkflowFile.getProcessImageContentType()).isEqualTo(DEFAULT_PROCESS_IMAGE_CONTENT_TYPE);
        assertThat(testActivitiWorkflowFile.getUploadTime()).isEqualTo(DEFAULT_UPLOAD_TIME);
        assertThat(testActivitiWorkflowFile.getUploadUser()).isEqualTo(DEFAULT_UPLOAD_USER);
    }

    @Test
    @Transactional
    public void createActivitiWorkflowFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activitiWorkflowFileRepository.findAll().size();

        // Create the ActivitiWorkflowFile with an existing ID
        activitiWorkflowFile.setId(1L);
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO = activitiWorkflowFileMapper.toDto(activitiWorkflowFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivitiWorkflowFileMockMvc.perform(post("/api/activiti-workflow-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiWorkflowFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivitiWorkflowFile in the database
        List<ActivitiWorkflowFile> activitiWorkflowFileList = activitiWorkflowFileRepository.findAll();
        assertThat(activitiWorkflowFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFiles() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList
        restActivitiWorkflowFileMockMvc.perform(get("/api/activiti-workflow-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activitiWorkflowFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].workflowName").value(hasItem(DEFAULT_WORKFLOW_NAME.toString())))
            .andExpect(jsonPath("$.[*].processDefinitionContentType").value(hasItem(DEFAULT_PROCESS_DEFINITION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].processDefinition").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROCESS_DEFINITION))))
            .andExpect(jsonPath("$.[*].processImageContentType").value(hasItem(DEFAULT_PROCESS_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].processImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROCESS_IMAGE))))
            .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].uploadUser").value(hasItem(DEFAULT_UPLOAD_USER.toString())));
    }

    @Test
    @Transactional
    public void getActivitiWorkflowFile() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get the activitiWorkflowFile
        restActivitiWorkflowFileMockMvc.perform(get("/api/activiti-workflow-files/{id}", activitiWorkflowFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activitiWorkflowFile.getId().intValue()))
            .andExpect(jsonPath("$.workflowName").value(DEFAULT_WORKFLOW_NAME.toString()))
            .andExpect(jsonPath("$.processDefinitionContentType").value(DEFAULT_PROCESS_DEFINITION_CONTENT_TYPE))
            .andExpect(jsonPath("$.processDefinition").value(Base64Utils.encodeToString(DEFAULT_PROCESS_DEFINITION)))
            .andExpect(jsonPath("$.processImageContentType").value(DEFAULT_PROCESS_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.processImage").value(Base64Utils.encodeToString(DEFAULT_PROCESS_IMAGE)))
            .andExpect(jsonPath("$.uploadTime").value(DEFAULT_UPLOAD_TIME.toString()))
            .andExpect(jsonPath("$.uploadUser").value(DEFAULT_UPLOAD_USER.toString()));
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByWorkflowNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where workflowName equals to DEFAULT_WORKFLOW_NAME
        defaultActivitiWorkflowFileShouldBeFound("workflowName.equals=" + DEFAULT_WORKFLOW_NAME);

        // Get all the activitiWorkflowFileList where workflowName equals to UPDATED_WORKFLOW_NAME
        defaultActivitiWorkflowFileShouldNotBeFound("workflowName.equals=" + UPDATED_WORKFLOW_NAME);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByWorkflowNameIsInShouldWork() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where workflowName in DEFAULT_WORKFLOW_NAME or UPDATED_WORKFLOW_NAME
        defaultActivitiWorkflowFileShouldBeFound("workflowName.in=" + DEFAULT_WORKFLOW_NAME + "," + UPDATED_WORKFLOW_NAME);

        // Get all the activitiWorkflowFileList where workflowName equals to UPDATED_WORKFLOW_NAME
        defaultActivitiWorkflowFileShouldNotBeFound("workflowName.in=" + UPDATED_WORKFLOW_NAME);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByWorkflowNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where workflowName is not null
        defaultActivitiWorkflowFileShouldBeFound("workflowName.specified=true");

        // Get all the activitiWorkflowFileList where workflowName is null
        defaultActivitiWorkflowFileShouldNotBeFound("workflowName.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByUploadTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where uploadTime equals to DEFAULT_UPLOAD_TIME
        defaultActivitiWorkflowFileShouldBeFound("uploadTime.equals=" + DEFAULT_UPLOAD_TIME);

        // Get all the activitiWorkflowFileList where uploadTime equals to UPDATED_UPLOAD_TIME
        defaultActivitiWorkflowFileShouldNotBeFound("uploadTime.equals=" + UPDATED_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByUploadTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where uploadTime in DEFAULT_UPLOAD_TIME or UPDATED_UPLOAD_TIME
        defaultActivitiWorkflowFileShouldBeFound("uploadTime.in=" + DEFAULT_UPLOAD_TIME + "," + UPDATED_UPLOAD_TIME);

        // Get all the activitiWorkflowFileList where uploadTime equals to UPDATED_UPLOAD_TIME
        defaultActivitiWorkflowFileShouldNotBeFound("uploadTime.in=" + UPDATED_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByUploadTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where uploadTime is not null
        defaultActivitiWorkflowFileShouldBeFound("uploadTime.specified=true");

        // Get all the activitiWorkflowFileList where uploadTime is null
        defaultActivitiWorkflowFileShouldNotBeFound("uploadTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByUploadUserIsEqualToSomething() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where uploadUser equals to DEFAULT_UPLOAD_USER
        defaultActivitiWorkflowFileShouldBeFound("uploadUser.equals=" + DEFAULT_UPLOAD_USER);

        // Get all the activitiWorkflowFileList where uploadUser equals to UPDATED_UPLOAD_USER
        defaultActivitiWorkflowFileShouldNotBeFound("uploadUser.equals=" + UPDATED_UPLOAD_USER);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByUploadUserIsInShouldWork() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where uploadUser in DEFAULT_UPLOAD_USER or UPDATED_UPLOAD_USER
        defaultActivitiWorkflowFileShouldBeFound("uploadUser.in=" + DEFAULT_UPLOAD_USER + "," + UPDATED_UPLOAD_USER);

        // Get all the activitiWorkflowFileList where uploadUser equals to UPDATED_UPLOAD_USER
        defaultActivitiWorkflowFileShouldNotBeFound("uploadUser.in=" + UPDATED_UPLOAD_USER);
    }

    @Test
    @Transactional
    public void getAllActivitiWorkflowFilesByUploadUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);

        // Get all the activitiWorkflowFileList where uploadUser is not null
        defaultActivitiWorkflowFileShouldBeFound("uploadUser.specified=true");

        // Get all the activitiWorkflowFileList where uploadUser is null
        defaultActivitiWorkflowFileShouldNotBeFound("uploadUser.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultActivitiWorkflowFileShouldBeFound(String filter) throws Exception {
        restActivitiWorkflowFileMockMvc.perform(get("/api/activiti-workflow-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activitiWorkflowFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].workflowName").value(hasItem(DEFAULT_WORKFLOW_NAME.toString())))
            .andExpect(jsonPath("$.[*].processDefinitionContentType").value(hasItem(DEFAULT_PROCESS_DEFINITION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].processDefinition").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROCESS_DEFINITION))))
            .andExpect(jsonPath("$.[*].processImageContentType").value(hasItem(DEFAULT_PROCESS_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].processImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROCESS_IMAGE))))
            .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].uploadUser").value(hasItem(DEFAULT_UPLOAD_USER.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultActivitiWorkflowFileShouldNotBeFound(String filter) throws Exception {
        restActivitiWorkflowFileMockMvc.perform(get("/api/activiti-workflow-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingActivitiWorkflowFile() throws Exception {
        // Get the activitiWorkflowFile
        restActivitiWorkflowFileMockMvc.perform(get("/api/activiti-workflow-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivitiWorkflowFile() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);
        int databaseSizeBeforeUpdate = activitiWorkflowFileRepository.findAll().size();

        // Update the activitiWorkflowFile
        ActivitiWorkflowFile updatedActivitiWorkflowFile = activitiWorkflowFileRepository.findOne(activitiWorkflowFile.getId());
        // Disconnect from session so that the updates on updatedActivitiWorkflowFile are not directly saved in db
        em.detach(updatedActivitiWorkflowFile);
        updatedActivitiWorkflowFile
            .workflowName(UPDATED_WORKFLOW_NAME)
            .processDefinition(UPDATED_PROCESS_DEFINITION)
            .processDefinitionContentType(UPDATED_PROCESS_DEFINITION_CONTENT_TYPE)
            .processImage(UPDATED_PROCESS_IMAGE)
            .processImageContentType(UPDATED_PROCESS_IMAGE_CONTENT_TYPE)
            .uploadTime(UPDATED_UPLOAD_TIME)
            .uploadUser(UPDATED_UPLOAD_USER);
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO = activitiWorkflowFileMapper.toDto(updatedActivitiWorkflowFile);

        restActivitiWorkflowFileMockMvc.perform(put("/api/activiti-workflow-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiWorkflowFileDTO)))
            .andExpect(status().isOk());

        // Validate the ActivitiWorkflowFile in the database
        List<ActivitiWorkflowFile> activitiWorkflowFileList = activitiWorkflowFileRepository.findAll();
        assertThat(activitiWorkflowFileList).hasSize(databaseSizeBeforeUpdate);
        ActivitiWorkflowFile testActivitiWorkflowFile = activitiWorkflowFileList.get(activitiWorkflowFileList.size() - 1);
        assertThat(testActivitiWorkflowFile.getWorkflowName()).isEqualTo(UPDATED_WORKFLOW_NAME);
        assertThat(testActivitiWorkflowFile.getProcessDefinition()).isEqualTo(UPDATED_PROCESS_DEFINITION);
        assertThat(testActivitiWorkflowFile.getProcessDefinitionContentType()).isEqualTo(UPDATED_PROCESS_DEFINITION_CONTENT_TYPE);
        assertThat(testActivitiWorkflowFile.getProcessImage()).isEqualTo(UPDATED_PROCESS_IMAGE);
        assertThat(testActivitiWorkflowFile.getProcessImageContentType()).isEqualTo(UPDATED_PROCESS_IMAGE_CONTENT_TYPE);
        assertThat(testActivitiWorkflowFile.getUploadTime()).isEqualTo(UPDATED_UPLOAD_TIME);
        assertThat(testActivitiWorkflowFile.getUploadUser()).isEqualTo(UPDATED_UPLOAD_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingActivitiWorkflowFile() throws Exception {
        int databaseSizeBeforeUpdate = activitiWorkflowFileRepository.findAll().size();

        // Create the ActivitiWorkflowFile
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO = activitiWorkflowFileMapper.toDto(activitiWorkflowFile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivitiWorkflowFileMockMvc.perform(put("/api/activiti-workflow-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiWorkflowFileDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivitiWorkflowFile in the database
        List<ActivitiWorkflowFile> activitiWorkflowFileList = activitiWorkflowFileRepository.findAll();
        assertThat(activitiWorkflowFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivitiWorkflowFile() throws Exception {
        // Initialize the database
        activitiWorkflowFileRepository.saveAndFlush(activitiWorkflowFile);
        int databaseSizeBeforeDelete = activitiWorkflowFileRepository.findAll().size();

        // Get the activitiWorkflowFile
        restActivitiWorkflowFileMockMvc.perform(delete("/api/activiti-workflow-files/{id}", activitiWorkflowFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActivitiWorkflowFile> activitiWorkflowFileList = activitiWorkflowFileRepository.findAll();
        assertThat(activitiWorkflowFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivitiWorkflowFile.class);
        ActivitiWorkflowFile activitiWorkflowFile1 = new ActivitiWorkflowFile();
        activitiWorkflowFile1.setId(1L);
        ActivitiWorkflowFile activitiWorkflowFile2 = new ActivitiWorkflowFile();
        activitiWorkflowFile2.setId(activitiWorkflowFile1.getId());
        assertThat(activitiWorkflowFile1).isEqualTo(activitiWorkflowFile2);
        activitiWorkflowFile2.setId(2L);
        assertThat(activitiWorkflowFile1).isNotEqualTo(activitiWorkflowFile2);
        activitiWorkflowFile1.setId(null);
        assertThat(activitiWorkflowFile1).isNotEqualTo(activitiWorkflowFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivitiWorkflowFileDTO.class);
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO1 = new ActivitiWorkflowFileDTO();
        activitiWorkflowFileDTO1.setId(1L);
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO2 = new ActivitiWorkflowFileDTO();
        assertThat(activitiWorkflowFileDTO1).isNotEqualTo(activitiWorkflowFileDTO2);
        activitiWorkflowFileDTO2.setId(activitiWorkflowFileDTO1.getId());
        assertThat(activitiWorkflowFileDTO1).isEqualTo(activitiWorkflowFileDTO2);
        activitiWorkflowFileDTO2.setId(2L);
        assertThat(activitiWorkflowFileDTO1).isNotEqualTo(activitiWorkflowFileDTO2);
        activitiWorkflowFileDTO1.setId(null);
        assertThat(activitiWorkflowFileDTO1).isNotEqualTo(activitiWorkflowFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activitiWorkflowFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activitiWorkflowFileMapper.fromId(null)).isNull();
    }
}
