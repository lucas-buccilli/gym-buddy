package com.example.gymbuddy.implementation.database.specifications;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class MachineHistorySpecificationsTest {

    @Test
    void shouldGetMachineId() {
        Root<MachineHistory> root = mock(Root.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        Path<Object> machinePath = mock(Path.class);
        Path<Object> idPath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("machine")).thenReturn(machinePath);
        when(machinePath.get("id")).thenReturn(idPath);
        when(criteriaBuilder.equal(idPath, 1)).thenReturn(predicate);

        assertEquals(predicate, MachineHistorySpecifications.hasMachineId(1).toPredicate(root, null, criteriaBuilder));
    }

    @Test
    void hasMemberId() {

        Root<MachineHistory> root = mock(Root.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        Path<Object> memberPath = mock(Path.class);
        Path<Object> idPath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("member")).thenReturn(memberPath);
        when(memberPath.get("id")).thenReturn(idPath);
        when(criteriaBuilder.equal(idPath, 1)).thenReturn(predicate);

        assertEquals(predicate, MachineHistorySpecifications.hasMemberId(1).toPredicate(root, null, criteriaBuilder));
    }
}