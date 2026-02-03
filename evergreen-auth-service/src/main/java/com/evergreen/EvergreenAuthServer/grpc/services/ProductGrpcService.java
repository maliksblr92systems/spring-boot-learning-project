package com.evergreen.EvergreenAuthServer.grpc.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.evergreen.EvergreenAuthServer.models.Product;
import com.evergreen.EvergreenAuthServer.repositories.ProductRepository;
import com.evergreen.lib.ProductServiceGrpc;
import com.evergreen.lib.ValidateStockRequest;
import com.evergreen.lib.ValidateStockResponse;
import com.evergreen.lib.mappers.HttpStatusAndGrpcStatusMapper;
import com.evergreen.lib.utils.ApiException;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validateStock(ValidateStockRequest request, StreamObserver<ValidateStockResponse> responseObserver) {
        try {
            final Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> {
                throw ApiException.notFound("Product not found.");
            });
            final boolean available = product.getStock() > request.getQuantity();
            final ValidateStockResponse response = ValidateStockResponse.newBuilder().setAvailable(available).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (

        ApiException e) {

            responseObserver.onError(HttpStatusAndGrpcStatusMapper.toGrpc(e.getHttpStatus()).withDescription(e.getMessage()).asException());

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription("Unexpected error: " + e.getMessage()).withCause(e).asRuntimeException());
        }
    }
}
