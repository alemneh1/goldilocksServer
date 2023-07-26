package GoldilocksProd.com.Server.services.documentaryService;

import GoldilocksProd.com.Server.projects.DocumentaryProject;

import java.util.List;

public interface DocumentaryProjectServices {
    public List<DocumentaryProject> listAllDocumentaryProjects();

    public DocumentaryProject createDocumentaryProject(DocumentaryProject documentaryProject);

    public void deleteDocumentaryProject(DocumentaryProject documentaryProject);

    public DocumentaryProject updateDocumentaryProject(DocumentaryProject documentaryProject);

    public DocumentaryProject getDocumentaryProjectById(Long id);


}
