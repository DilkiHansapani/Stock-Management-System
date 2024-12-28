package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.MaterialDTOWithoutId;
import Assignment.StockManagementSystem.dto.MaterialsDTOWithoutInventories;
import Assignment.StockManagementSystem.models.Materials;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.MaterialsRepository;
import Assignment.StockManagementSystem.services.MaterialsService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialsServiceImp implements MaterialsService {

    private static final Logger logger = LogManager.getLogger(MaterialsServiceImp.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MaterialsRepository materialsRepository;

    @Override
    public Materials addMaterial(MaterialDTOWithoutId materialDTO) {
        try {

            Optional<Materials> existingMaterial = materialsRepository.findByMaterialNameAndMaterialType(
                    materialDTO.getMaterialName(),
                    materialDTO.getMaterialType()
            );

            if (existingMaterial.isPresent()) {
                throw new DuplicateResourceException("Material with the same name and type already exists");
            }

            Materials material = new Materials();

            material.setMaterialName(materialDTO.getMaterialName());
            material.setMaterialType(materialDTO.getMaterialType());

            return materialsRepository.save(material);

        }catch (DuplicateResourceException ex){
            logger.error("Material with the same name and type already exists",ex);
            throw ex;
        }catch (Exception ex) {
            logger.error("Error occurred adding material", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public Page<MaterialsDTOWithoutInventories> getMaterials(String searchTerm, Pageable pageable) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return materialsRepository.findMaterials(null, pageable)
                        .map(this::convertToMaterialDTO);
            }

            Page<Materials> materials = materialsRepository.findMaterials(searchTerm, pageable);
            return materials.map(this::convertToMaterialDTO);

        } catch (Exception ex) {
            logger.error("Error occurred retrieving materials", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Materials getMaterialById(int materialId) {
        try {
            return materialsRepository.findById(materialId)
                    .orElseThrow(() -> new ResourceNotFoundException("Material not found with ID: " + materialId));
        }catch (ResourceNotFoundException ex){
            logger.error("Material not found with ID: " + materialId);
            throw ex;
        }catch (Exception ex) {
            logger.error("Error occurred retrieving materials", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Materials> getAllMaterials(){
        try{
            return materialsRepository.findAll();
        }catch (Exception ex) {
            logger.error("Error occurred retrieving materials", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Materials updateMaterial(int materialId, MaterialDTOWithoutId updatedMaterial) {
        try {
            if (materialId == 0) {
                throw new BadRequestException("Material ID must be provided.");
            }

            Materials existingMaterial = materialsRepository.findById(materialId)
                    .orElseThrow(() -> new ResourceNotFoundException("Material with ID " + materialId + " not found."));

            existingMaterial.setMaterialName(updatedMaterial.getMaterialName());
            existingMaterial.setMaterialType(updatedMaterial.getMaterialType());

            return materialsRepository.save(existingMaterial);
        } catch (ResourceNotFoundException | BadRequestException | DuplicateResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error occurred while updating material with ID " + materialId, ex);
            throw new InternalServerErrorException("An error occurred while updating the material. Please try again.");
        }
    }

    private MaterialsDTOWithoutInventories convertToMaterialDTO(Materials material) {
        MaterialsDTOWithoutInventories dto = new MaterialsDTOWithoutInventories();
        dto.setMaterialId(material.getMaterialId());
        dto.setMaterialName(material.getMaterialName());
        dto.setMaterialType(material.getMaterialType());
        return dto;
    }
}
