package Assignment.StockManagementSystem.ServiceTests;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.MaterialDTOWithoutId;
import Assignment.StockManagementSystem.dto.MaterialsDTOWithoutInventories;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.models.Materials;
import Assignment.StockManagementSystem.repositories.MaterialsRepository;
import Assignment.StockManagementSystem.services.implementation.MaterialsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialsServiceImpTest {

    @InjectMocks
    private MaterialsServiceImp materialsService;

    @Mock
    private MaterialsRepository materialsRepository;

    private Materials material;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        material = new Materials();
        material.setMaterialId(1);
        material.setMaterialName("Test Material");
        material.setMaterialType("Raw Material");
    }

    @Test
    void testAddMaterialSuccess() {
        MaterialDTOWithoutId materialDTO = new MaterialDTOWithoutId();
        materialDTO.setMaterialName("Test Material");
        materialDTO.setMaterialType("Raw Material");

        when(materialsRepository.findByMaterialNameAndMaterialType(materialDTO.getMaterialName(), materialDTO.getMaterialType()))
                .thenReturn(Optional.empty());
        when(materialsRepository.save(any(Materials.class))).thenReturn(material);

        Materials result = materialsService.addMaterial(materialDTO);

        assertNotNull(result);
        assertEquals(material.getMaterialName(), result.getMaterialName());
        verify(materialsRepository, times(1)).save(any(Materials.class));
    }

    @Test
    void testAddMaterialDuplicateResourceException() {
        MaterialDTOWithoutId materialDTO = new MaterialDTOWithoutId();
        materialDTO.setMaterialName("Test Material");
        materialDTO.setMaterialType("Raw Material");

        when(materialsRepository.findByMaterialNameAndMaterialType(materialDTO.getMaterialName(), materialDTO.getMaterialType()))
                .thenReturn(Optional.of(material));

        assertThrows(DuplicateResourceException.class, () -> materialsService.addMaterial(materialDTO));
        verify(materialsRepository, never()).save(any(Materials.class));
    }

    @Test
    void testGetMaterialsWithSearchTerm() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Materials> page = new PageImpl<>(Collections.singletonList(material));

        when(materialsRepository.findMaterials("Test", pageable)).thenReturn(page);

        Page<MaterialsDTOWithoutInventories> result = materialsService.getMaterials("Test", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(materialsRepository, times(1)).findMaterials("Test", pageable);
    }

    @Test
    void testGetMaterialsWithoutSearchTerm() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Materials> page = new PageImpl<>(Collections.singletonList(material));

        when(materialsRepository.findMaterials(null, pageable)).thenReturn(page);

        Page<MaterialsDTOWithoutInventories> result = materialsService.getMaterials(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(materialsRepository, times(1)).findMaterials(null, pageable);
    }

    @Test
    void testGetMaterialByIdSuccess() {
        when(materialsRepository.findById(1)).thenReturn(Optional.of(material));

        Materials result = materialsService.getMaterialById(1);

        assertNotNull(result);
        assertEquals(material.getMaterialName(), result.getMaterialName());
        verify(materialsRepository, times(1)).findById(1);
    }

    @Test
    void testGetMaterialByIdNotFound() {
        when(materialsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> materialsService.getMaterialById(1));
        verify(materialsRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllMaterialsSuccess() {
        when(materialsRepository.findAll()).thenReturn(Collections.singletonList(material));

        List<Materials> result = materialsService.getAllMaterials();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(materialsRepository, times(1)).findAll();
    }

    @Test
    void testUpdateMaterialSuccess() {
        MaterialDTOWithoutId updatedMaterialDTO = new MaterialDTOWithoutId();
        updatedMaterialDTO.setMaterialName("Updated Material");
        updatedMaterialDTO.setMaterialType("Updated Type");

        when(materialsRepository.findById(1)).thenReturn(Optional.of(material));
        when(materialsRepository.save(any(Materials.class))).thenReturn(material);

        Materials result = materialsService.updateMaterial(1, updatedMaterialDTO);

        assertNotNull(result);
        assertEquals(updatedMaterialDTO.getMaterialName(), result.getMaterialName());
        verify(materialsRepository, times(1)).save(any(Materials.class));
    }

    @Test
    void testUpdateMaterialNotFound() {
        MaterialDTOWithoutId updatedMaterialDTO = new MaterialDTOWithoutId();
        updatedMaterialDTO.setMaterialName("Updated Material");
        updatedMaterialDTO.setMaterialType("Updated Type");

        when(materialsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> materialsService.updateMaterial(1, updatedMaterialDTO));
        verify(materialsRepository, never()).save(any(Materials.class));
    }
}
