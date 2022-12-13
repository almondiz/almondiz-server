package org.almondiz.almondiz.nut;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.NutExistedException;
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

    @Transactional
    public Nut getNutByNutName(String nutName) {
        return nutRepository.findByNutName(nutName).orElseThrow(NutNotFoundException::new);
    }
    @Transactional
    public Nut create(String nutName) {
        if(nutRepository.findByNutName(nutName).isPresent()){
            throw new NutExistedException();
        }

        return nutRepository.save(Nut.builder()
                                      .nutName(nutName)
                                     .build());
    }

    @Transactional
    public void deleteById(Long id) {
        Nut nut = nutRepository.findById(id).orElseThrow(NutNotFoundException::new);

        nutRepository.delete(nut);
    }

    @Transactional
    public void delete(String nutName) {
        Nut nut = nutRepository.findByNutName(nutName).orElseThrow(NutNotFoundException::new);

        nutRepository.delete(nut);
    }
}
