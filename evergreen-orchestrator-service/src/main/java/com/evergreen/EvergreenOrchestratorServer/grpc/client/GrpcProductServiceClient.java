package com.evergreen.EvergreenOrchestratorServer.grpc.client;

import org.springframework.stereotype.Service;

import com.evergreen.lib.ProductServiceGrpc;
import com.evergreen.lib.ValidateStockRequest;
import com.evergreen.lib.ValidateStockResponse;
import com.evergreen.lib.mappers.HttpStatusAndGrpcStatusMapper;
import com.evergreen.lib.utils.ApiError;
import com.evergreen.lib.utils.ApiException;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@Slf4j
public class GrpcProductServiceClient {

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    public boolean validateStock(int productId, int quantity) {
        try {
            ValidateStockRequest request = ValidateStockRequest.newBuilder().setQuantity(quantity).setProductId(productId).build();
            ValidateStockResponse response = productServiceStub.validateStock(request);
            return response.getAvailable();
        } catch (StatusRuntimeException ex) {
            throw new ApiException(new ApiError(ex.getStatus().getDescription()), HttpStatusAndGrpcStatusMapper.toHttp(ex.getStatus()));
        }

    }
}
