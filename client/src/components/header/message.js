import React from 'react';

const Message = (item) => {
    return (
        <a href="#" class="dropdown-item">
            <div class="media">
                <img src="dist/img/user1-128x128.jpg" alt="User Avatar" class="img-size-50 mr-3 img-circle" />
                <div class="media-body">
                    <h3 class="dropdown-item-title">
                        Brad Diesel
                            <span class="float-right text-sm text-danger"><i class="fas fa-star"></i></span>
                    </h3>
                    <p class="text-sm">Call me whenever you can...</p>
                    <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
                </div>
            </div>
        </a>
    )
}

export default Message;