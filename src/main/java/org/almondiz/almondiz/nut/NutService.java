package org.almondiz.almondiz.nut;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.NutNotFoundException;
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.nut.entity.NutRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutService {

    private final NutRepository nutRepository;

    @Transactional
    public String getNutNameById(Long nutId){
        return this.getNutById(nutId).getNutName();
    }

    @Transactional
    public Nut getNutById(Long nutId){
        return nutRepository.findById(nutId).orElseThrow(NutNotFoundException::new);
    }

}
