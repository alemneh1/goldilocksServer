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
@Table(name = "commercial_project")
public class CommercialProject extends ProjectModel {

    public String commercialDescription;
    public CommercialProject(Long id, String projectType, String title, String date, String thumbnailPath, String videoPath, String commercialDescription) {
        super(id, projectType, title, date, thumbnailPath, videoPath);
        this.commercialDescription = commercialDescription;
    }
}
