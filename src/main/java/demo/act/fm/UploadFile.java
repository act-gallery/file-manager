package demo.act.fm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="file")
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String description;

    /**
     * key to fetch the file from {@link org.osgl.storage.IStorageService}
     */
    public String ssKey;
}
