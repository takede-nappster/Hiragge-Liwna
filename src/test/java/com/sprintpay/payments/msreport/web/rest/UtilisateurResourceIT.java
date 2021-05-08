package com.sprintpay.payments.msreport.web.rest;

import com.sprintpay.payments.msreport.TestApp;
import com.sprintpay.payments.msreport.domain.Utilisateur;
import com.sprintpay.payments.msreport.domain.Match;
import com.sprintpay.payments.msreport.repository.UtilisateurRepository;
import com.sprintpay.payments.msreport.service.UtilisateurService;
import com.sprintpay.payments.msreport.service.dto.UtilisateurDTO;
import com.sprintpay.payments.msreport.service.mapper.UtilisateurMapper;
import com.sprintpay.payments.msreport.service.dto.UtilisateurCriteria;
import com.sprintpay.payments.msreport.service.UtilisateurQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sprintpay.payments.msreport.domain.enumeration.UserType;
/**
 * Integration tests for the {@link UtilisateurResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UtilisateurResourceIT {

    private static final UserType DEFAULT_USER_TYPE = UserType.ADMINISTRATEUR;
    private static final UserType UPDATED_USER_TYPE = UserType.SIMPLE;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private UtilisateurRepository utilisateurRepositoryMock;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @Mock
    private UtilisateurService utilisateurServiceMock;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurQueryService utilisateurQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUtilisateurMockMvc;

    private Utilisateur utilisateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utilisateur createEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .userType(DEFAULT_USER_TYPE);
        return utilisateur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utilisateur createUpdatedEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .userType(UPDATED_USER_TYPE);
        return utilisateur;
    }

    @BeforeEach
    public void initTest() {
        utilisateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();
        // Create the Utilisateur
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isCreated());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
    }

    @Test
    @Transactional
    public void createUtilisateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur with an existing ID
        utilisateur.setId(1L);
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUtilisateurs() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUtilisateursWithEagerRelationshipsIsEnabled() throws Exception {
        when(utilisateurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUtilisateurMockMvc.perform(get("/api/utilisateurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(utilisateurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUtilisateursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(utilisateurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUtilisateurMockMvc.perform(get("/api/utilisateurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(utilisateurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", utilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(utilisateur.getId().intValue()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getUtilisateursByIdFiltering() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        Long id = utilisateur.getId();

        defaultUtilisateurShouldBeFound("id.equals=" + id);
        defaultUtilisateurShouldNotBeFound("id.notEquals=" + id);

        defaultUtilisateurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUtilisateurShouldNotBeFound("id.greaterThan=" + id);

        defaultUtilisateurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUtilisateurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUtilisateursByUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where userType equals to DEFAULT_USER_TYPE
        defaultUtilisateurShouldBeFound("userType.equals=" + DEFAULT_USER_TYPE);

        // Get all the utilisateurList where userType equals to UPDATED_USER_TYPE
        defaultUtilisateurShouldNotBeFound("userType.equals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUserTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where userType not equals to DEFAULT_USER_TYPE
        defaultUtilisateurShouldNotBeFound("userType.notEquals=" + DEFAULT_USER_TYPE);

        // Get all the utilisateurList where userType not equals to UPDATED_USER_TYPE
        defaultUtilisateurShouldBeFound("userType.notEquals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where userType in DEFAULT_USER_TYPE or UPDATED_USER_TYPE
        defaultUtilisateurShouldBeFound("userType.in=" + DEFAULT_USER_TYPE + "," + UPDATED_USER_TYPE);

        // Get all the utilisateurList where userType equals to UPDATED_USER_TYPE
        defaultUtilisateurShouldNotBeFound("userType.in=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where userType is not null
        defaultUtilisateurShouldBeFound("userType.specified=true");

        // Get all the utilisateurList where userType is null
        defaultUtilisateurShouldNotBeFound("userType.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);
        Match match = MatchResourceIT.createEntity(em);
        em.persist(match);
        em.flush();
        utilisateur.addMatch(match);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long matchId = match.getId();

        // Get all the utilisateurList where match equals to matchId
        defaultUtilisateurShouldBeFound("matchId.equals=" + matchId);

        // Get all the utilisateurList where match equals to matchId + 1
        defaultUtilisateurShouldNotBeFound("matchId.equals=" + (matchId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUtilisateurShouldBeFound(String filter) throws Exception {
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())));

        // Check, that the count call also returns 1
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUtilisateurShouldNotBeFound(String filter) throws Exception {
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUtilisateur() throws Exception {
        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur
        Utilisateur updatedUtilisateur = utilisateurRepository.findById(utilisateur.getId()).get();
        // Disconnect from session so that the updates on updatedUtilisateur are not directly saved in db
        em.detach(updatedUtilisateur);
        updatedUtilisateur
            .userType(UPDATED_USER_TYPE);
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(updatedUtilisateur);

        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getUserType()).isEqualTo(UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Create the Utilisateur
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeDelete = utilisateurRepository.findAll().size();

        // Delete the utilisateur
        restUtilisateurMockMvc.perform(delete("/api/utilisateurs/{id}", utilisateur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
