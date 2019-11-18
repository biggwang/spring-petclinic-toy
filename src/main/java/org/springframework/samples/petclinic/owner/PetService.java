package org.springframework.samples.petclinic.owner;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<PetType> findPetTypes() {
        return petRepository.findPetTypes();
    }

}
