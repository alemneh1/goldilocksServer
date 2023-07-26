package GoldilocksProd.com.Server.projects;


import GoldilocksProd.com.Server.model.ProjectModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "documentary_project")
public class DocumentaryProject extends ProjectModel {

    private String documentaryDirector;
    private String documentaryLength;
    private String synopsis;

    public DocumentaryProject(Long id, String projectType, String title, String date,
                              String thumbnailPath, String videoPath, String documentaryDirector, String documentaryLength, String synopsis){
        super(id, projectType, title, date, thumbnailPath, videoPath);
        this.documentaryDirector = documentaryDirector;
        this.documentaryLength = documentaryLength;
        this.synopsis = synopsis;
    }


}
