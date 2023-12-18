package api.com.medhead.service;

import api.com.medhead.model.SpecialityGroup;
import api.com.medhead.repository.SpecialityGroupRepository;
import api.com.medhead.repository.SpecialityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SpecialityGroupServiceTest {

    @InjectMocks
    private SpecialityGroupService specialityGroupService;

    @Mock
    private SpecialityGroupRepository specialityGroupRepositoryMock;

    private SpecialityGroup specialityGroup0 = new SpecialityGroup("Groupe dentaire");
    private SpecialityGroup specialityGroup1 = new SpecialityGroup("Médecine d'urgence");
    private SpecialityGroup specialityGroup2 = new SpecialityGroup("Groupe de médecine générale");
    private SpecialityGroup specialityGroup3 = new SpecialityGroup("Obstétrique et gynécologie");
    private SpecialityGroup specialityGroup4 = new SpecialityGroup("Groupe pédiatrique");

    private List<SpecialityGroup> specialityGroups = new ArrayList<>();

    @BeforeEach
    public void setup_Test() {
        specialityGroup0.setId(1);
        specialityGroup1.setId(2);
        specialityGroup2.setId(3);
        specialityGroup3.setId(4);
        specialityGroup4.setId(5);

        specialityGroups.add(specialityGroup0);
        specialityGroups.add(specialityGroup1);
        specialityGroups.add(specialityGroup2);
        specialityGroups.add(specialityGroup3);
        specialityGroups.add(specialityGroup4);
    }

    @Test
    void findByName() {
        when(specialityGroupService.findByName(specialityGroup4.getName())).thenReturn(specialityGroup4);
        SpecialityGroup specialityGroup = specialityGroupService.findByName(specialityGroup4.getName());
        assertEquals("Groupe pédiatrique", specialityGroup.getName());
    }

    @Test
    void getAllSpecialityGroups() {
        when(specialityGroupService.getAllSpecialityGroups()).thenReturn(specialityGroups);
        List<SpecialityGroup> specialityGroups = specialityGroupService.getAllSpecialityGroups();
        assertEquals(5, specialityGroups.size());
        assertEquals("Médecine d'urgence", specialityGroups.get(1).getName());
    }
}