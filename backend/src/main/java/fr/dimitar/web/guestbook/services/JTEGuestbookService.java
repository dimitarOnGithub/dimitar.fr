package fr.dimitar.web.guestbook.services;

import fr.dimitar.web.guestbook.GuestbookEntry;
import fr.dimitar.web.guestbook.GuestbookRepository;
import fr.dimitar.web.guestbook.dto.GuestbookModel;
import fr.dimitar.web.guestbook.forms.GuestbookForm;
import fr.dimitar.web.guestbook.mapper.GuestbookMapper;
import fr.dimitar.web.guestbook.specifications.GuestbookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JTEGuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Autowired
    public JTEGuestbookService(GuestbookRepository guestbookRepository){
        this.guestbookRepository = guestbookRepository;
    }

    public List<GuestbookModel> getGuestbook() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Specification<GuestbookEntry> spec = GuestbookSpecification.approvedOnly(true);
        return this.guestbookRepository.findAll(spec, sort)
                .stream()
                .map(GuestbookMapper::fromEntityToModel)
                .toList();
    }

    public void postGuestbook(GuestbookForm guestbookRequest) {
        GuestbookEntry entry = GuestbookMapper.fromFormToEntity(guestbookRequest);
        this.guestbookRepository.save(entry);
    }

    public Optional<GuestbookModel> findByIpAddress(String ipAddress) {
        Optional<GuestbookEntry> entry = this.guestbookRepository.findByIpAddress(ipAddress);
        return entry.map(GuestbookMapper::fromEntityToModel);
    }

}
