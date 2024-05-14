package com.ssafy.stellar.pieceOfKnowledge.repository;

import com.ssafy.stellar.pieceOfKnowledge.entity.PieceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<PieceEntity, Integer> {
}
