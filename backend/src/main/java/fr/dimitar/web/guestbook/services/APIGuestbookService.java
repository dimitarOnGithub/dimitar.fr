package fr.dimitar.web.guestbook.services;

import fr.dimitar.web.guestbook.GuestbookEntry;
import fr.dimitar.web.guestbook.GuestbookRepository;
import fr.dimitar.web.guestbook.dto.GuestbookRequest;
import fr.dimitar.web.guestbook.dto.GuestbookResponse;
import fr.dimitar.web.guestbook.filters.GuestbookFilter;
import fr.dimitar.web.guestbook.mapper.GuestbookMapper;
import fr.dimitar.web.guestbook.specifications.GuestbookSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class APIGuestbookService implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Autowired
    public APIGuestbookService(GuestbookRepository guestbookRepository){
        this.guestbookRepository = guestbookRepository;
    }

    @Override
    public Page<GuestbookResponse> getGuestbook(GuestbookFilter guestbookFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(guestbookFilter.getPage(), guestbookFilter.getPageSize(), sort);

        guestbookFilter.setApprovedOnly(true);
        Specification<GuestbookEntry> specification = GuestbookSpecificationBuilder.fromFilter(guestbookFilter);

        return this.guestbookRepository.findAll(specification, pageable).map(GuestbookMapper::fromEntityToResponse);
    }

    @Override
    public void postGuestbook(GuestbookRequest guestbookRequest) {
        GuestbookEntry entry = GuestbookMapper.fromRequestToEntity(guestbookRequest);
        this.guestbookRepository.save(entry);
    }

}
