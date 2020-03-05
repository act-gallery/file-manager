package demo.act.fm;

import act.controller.Controller;
import act.controller.annotation.UrlContext;
import act.db.DbBind;
import act.db.jpa.JPADao;
import org.osgl.mvc.annotation.DeleteAction;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.storage.ISObject;
import org.osgl.storage.IStorageService;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@UrlContext("fm")
public class FileManager extends Controller.Util {

    @Inject
    private IStorageService ss;

    @Inject
    JPADao<Integer, UploadFile> dao;

    /**
     * File manager home - render HTML template
     */
    @GetAction
    public void home() {
        Iterable<UploadFile> files = dao.findAll();
        renderTemplate("/fm.html", files);
    }

    /**
     * Upload a file to the service
     *
     * @param file the file to be uploaded
     * @return the key to access the file
     */
    @PostAction("upload")
    public void upload(ISObject file, String description) {
        String key = ss.getKey();
        ss.put(key, file);
        UploadFile uf = new UploadFile();
        uf.ssKey = key;
        uf.description = description;
        dao.save(uf);
        redirect("/fm");
    }

    /**
     * Download a file.
     *
     * @param __path everything in URL after `/download`, it is the key to access the file
     * @return the file been downloaded
     */
    @GetAction("download/...")
    public ISObject download(String __path) {
        return ss.get(__path);
    }

    @DeleteAction("{uploadFile}")
    public void delete(@DbBind @NotNull UploadFile uploadFile) {
        ss.remove(uploadFile.ssKey);
        dao.delete(uploadFile);
    }

}
