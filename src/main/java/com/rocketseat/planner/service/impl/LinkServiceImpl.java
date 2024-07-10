package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.dto.LinkCreateResponse;
import com.rocketseat.planner.dto.LinkData;
import com.rocketseat.planner.dto.LinkRequestPayload;
import com.rocketseat.planner.model.entity.Link;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.repository.LinkRepository;
import com.rocketseat.planner.service.LinkService;
import com.rocketseat.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    private final TripService tripService;

    @Override
    public LinkCreateResponse createLink(Trip trip, LinkRequestPayload payload) {

            Link link = new Link(trip, payload);
            linkRepository.save(link);

            return new LinkCreateResponse(link.getId());
    }

    @Override
    public List<LinkData> getLinksByTripId(UUID id) {
        Optional<Trip> trip = tripService.findById(id);
        List<LinkData> linkDataListData = new ArrayList<LinkData>();
        if (trip.isPresent()){
            List<Link> links = linkRepository.getByTripId(id);
            if (!links.isEmpty())
                linkDataListData = links.stream().map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl())).toList();
        }
        return linkDataListData;
    }
}
