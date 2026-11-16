package pe.edu.upeu.academia.service.generic;

import java.util.List;

public interface CrudService<REQ, RES, ID> {
    List<RES> findAll();

    RES findById(ID id);

    RES create(REQ request);

    RES update(ID id, REQ request);

    void deleteById(ID id);
}