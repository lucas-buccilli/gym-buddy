package com.example.gymbuddy.implementation.database.specifications;

class MachineHistorySpecificationsTest {

//    @Test
//    void shouldGetMachineId() {
//        Root<MachineHistory> root = mock(Root.class);
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        Path<Object> machinePath = mock(Path.class);
//        Path<Object> idPath = mock(Path.class);
//        Predicate predicate = mock(Predicate.class);
//
//        when(root.get("machine")).thenReturn(machinePath);
//        when(machinePath.get("id")).thenReturn(idPath);
//        when(criteriaBuilder.equal(idPath, 1)).thenReturn(predicate);
//
//        assertEquals(predicate, MachineHistorySpecifications.hasMachineId(1).toPredicate(root, null, criteriaBuilder));
//    }
//
//    @Test
//    void hasMemberId() {
//
//        Root<MachineHistory> root = mock(Root.class);
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        Path<Object> memberPath = mock(Path.class);
//        Path<Object> idPath = mock(Path.class);
//        Predicate predicate = mock(Predicate.class);
//
//        when(root.get("member")).thenReturn(memberPath);
//        when(memberPath.get("id")).thenReturn(idPath);
//        when(criteriaBuilder.equal(idPath, 1)).thenReturn(predicate);
//
//        assertEquals(predicate, MachineHistorySpecifications.hasMemberId(1).toPredicate(root, null, criteriaBuilder));
//    }
//
//    @Test
//    void hasWorkoutDate() {
//
//        Root<MachineHistory> root = mock(Root.class);
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        Path<Object> workoutDatePath = mock(Path.class);
//        Predicate predicate = mock(Predicate.class);
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        when(root.get("workoutDate")).thenReturn(workoutDatePath);
//        when(criteriaBuilder.equal(workoutDatePath, dateTime)).thenReturn(predicate);
//
//        assertEquals(predicate, MachineHistorySpecifications.hasWorkoutDate(dateTime).toPredicate(root, null, criteriaBuilder));
//    }
//
//    @Test
//    void shouldHaveWorkoutDateGreaterThanOrEqualTo() {
//
//        Root<MachineHistory> root = mock(Root.class);
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        Path<Object> workoutDatePath = mock(Path.class);
//        Predicate predicate = mock(Predicate.class);
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        when(root.get("workoutDate")).thenReturn(workoutDatePath);
//        when(criteriaBuilder.greaterThanOrEqualTo(any(), eq(dateTime))).thenReturn(predicate);
//
//        assertEquals(predicate, MachineHistorySpecifications.hasWorkoutDateGreaterThanOrEqualTo(dateTime).toPredicate(root, null, criteriaBuilder));
//    }
//
//    @Test
//    void shouldHaveWorkoutDateLessThanOrEqualTo() {
//
//        Root<MachineHistory> root = mock(Root.class);
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        Path<Object> workoutDatePath = mock(Path.class);
//        Predicate predicate = mock(Predicate.class);
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        when(root.get("workoutDate")).thenReturn(workoutDatePath);
//        when(criteriaBuilder.lessThanOrEqualTo(any(), eq(dateTime))).thenReturn(predicate);
//
//        assertEquals(predicate, MachineHistorySpecifications.hasWorkoutDateLessThanOrEqualTo(dateTime).toPredicate(root, null, criteriaBuilder));
//    }
}