package GoldilocksProd.com.Server.services.commercialService;

import GoldilocksProd.com.Server.projects.CommercialProject;
import GoldilocksProd.com.Server.repository.CommercialProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CommercialProjectServicesImp implements CommercialProjectServices{

    public final CommercialProjectRepository commercialProjectRepository;

    @Override
    public List<CommercialProject> listAllCommercialProject() {
       return commercialProjectRepository.findAll();
    }

    @Override
    public CommercialProject createCommercialProject(CommercialProject commercialProject) {
        return commercialProjectRepository.save(commercialProject);
    }

    @Override
    public void deleteCommercialProject(CommercialProject commercialProject) {
        try {
            if (commercialProjectRepository.existsById(commercialProject.getId())) {
                commercialProjectRepository.delete(commercialProject);
            } else {
                // Handle the case when the MusicProject doesn't exist
                throw new IllegalArgumentException("Commercial project not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to delete the Commercial project: " + e.getMessage());
        }
    }

    @Override
    public CommercialProject updateCommercialProject(CommercialProject commercialProject) {
        try {
            if (commercialProjectRepository.existsById(commercialProject.getId())) {
                return commercialProjectRepository.save(commercialProject);
            } else {
                // Handle the case when the MusicProject doesn't exist
                throw new IllegalArgumentException("Commercial project not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to update the Commercial project: " + e.getMessage());
        }
    }

    @Override
    public CommercialProject getCommercialProjectById(Long id) {
        Optional<CommercialProject> optionalCommercialProject = commercialProjectRepository.findById(id);
        return optionalCommercialProject.orElse(null);
    }
}
