package GoldilocksProd.com.Server.services.documentaryService;

import GoldilocksProd.com.Server.projects.DocumentaryProject;
import GoldilocksProd.com.Server.repository.DocumentaryProjectRepository;
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
public class DocumentaryProjectServiceImp implements DocumentaryProjectServices{

    private final DocumentaryProjectRepository documentaryProjectRepository;

    @Override
    public List<DocumentaryProject> listAllDocumentaryProjects() {
        return documentaryProjectRepository.findAll();
    }

    @Override
    public DocumentaryProject createDocumentaryProject(DocumentaryProject documentaryProject) {
        return documentaryProjectRepository.save(documentaryProject);
    }

    @Override
    public void deleteDocumentaryProject(DocumentaryProject documentaryProject) {
        try {
            if (documentaryProjectRepository.existsById(documentaryProject.getId())) {
                documentaryProjectRepository.delete(documentaryProject);
            } else {
                // Handle the case when the MovieProject doesn't exist
                throw new IllegalArgumentException("Documentary not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to delete the Documentary: " + e.getMessage());
        }
    }

    @Override
    public DocumentaryProject updateDocumentaryProject(DocumentaryProject documentaryProject) {
        try {
            if (documentaryProjectRepository.existsById(documentaryProject.getId())) {
                return documentaryProjectRepository.save(documentaryProject);
            } else {
                // Handle the case when the Documentary doesn't exist
                throw new IllegalArgumentException("Documentary Project not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to update the Documentary Project: " + e.getMessage());
        }
    }

    @Override
    public DocumentaryProject getDocumentaryProjectById(Long id) {
        Optional<DocumentaryProject> optionalDocumentaryProject = documentaryProjectRepository.findById(id);
        return optionalDocumentaryProject.orElse(null);
    }
}
