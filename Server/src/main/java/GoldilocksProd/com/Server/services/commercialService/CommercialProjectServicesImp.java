package GoldilocksProd.com.Server.services.commercialService;

import GoldilocksProd.com.Server.projects.CommercialProject;
import GoldilocksProd.com.Server.repository.CommercialProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    }
}
