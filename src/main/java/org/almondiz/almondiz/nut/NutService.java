package org.almondiz.almondiz.nut;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.NutNotFoundException;
import org.almondiz.almondiz.nut.entity.NutRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class NutService {

    private final NutRepository nutRepository;

    public String getNutNameById(Long nutId){
        String nutName = nutRepository.findById(nutId).orElseThrow(NutNotFoundException::new).getNutName();
        return nutName;
    }

}
