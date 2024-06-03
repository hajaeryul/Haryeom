import { PointerEvent, RefObject, useEffect, useRef, useState } from 'react';
import { IPenStyle } from '@/hooks/useClass';

interface IUseMyPaint {
    canvasRef: RefObject<HTMLCanvasElement>;
    backgroundImage?: Blob | string;
    penStyle: IPenStyle;
    dataChannels?: RTCDataChannel[];
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    erasePeerPaint?: (offset: any) => void;
}

const useMyPaint = ({
    canvasRef,
    backgroundImage,
    penStyle,
    dataChannels,
    erasePeerPaint,
}: IUseMyPaint) => {
    const contextRef = useRef<CanvasRenderingContext2D | null>(null);
    const canvasInformRef = useRef({
        width: 0,
        height: 0,
        pixelRatio: 1,
    });
    const [isDown, setIsDown] = useState<boolean>(false);

    useEffect(() => {
        const isBrowser = typeof window !== 'undefined';
        if (isBrowser) {
            canvasInformRef.current = {
                width: 0,
                height: 0,
                pixelRatio: window.devicePixelRatio > 1 ? 2 : 1,
            };
        }
    }, []);

    useEffect(() => {
        init();
    }, [canvasRef.current?.width, canvasRef.current?.height, backgroundImage]);

    useEffect(() => {
        if (!contextRef.current) return;
        contextRef.current.strokeStyle = penStyle.strokeStyle;
        contextRef.current.lineWidth = penStyle.lineWidth;

        dataChannels?.map((channel: RTCDataChannel) => {
            try {
                channel.send(
                    JSON.stringify({
                        updatedPenStyle: penStyle,
                    })
                );
            } catch (e) {
                console.log('전송 실패');
            }
        });
    }, [penStyle]);

    const init = () => {
        if (!canvasRef.current) return;
        const { clientWidth, clientHeight } = canvasRef.current;
        canvasSizeSetting(clientWidth, clientHeight);

        const context = canvasRef.current.getContext('2d');
        if (!context) return;
        canvasContextSetting(context);
        canvasBackgroundSetting(context);
    };

    const canvasSizeSetting = (width: number, height: number) => {
        if (!canvasRef.current) return;
        const resultWidth = width * canvasInformRef.current.pixelRatio;
        const resultHeight = height * canvasInformRef.current.pixelRatio;

        canvasRef.current.width = resultWidth;
        canvasRef.current.height = resultHeight;

        canvasInformRef.current.width = resultWidth;
        canvasInformRef.current.height = resultHeight;
    };

    const canvasContextSetting = (ctx: CanvasRenderingContext2D) => {
        if (!ctx) return;
        ctx.scale(canvasInformRef.current.pixelRatio, canvasInformRef.current.pixelRatio);
        ctx.lineCap = 'round';
        ctx.strokeStyle = penStyle.strokeStyle;
        ctx.lineWidth = penStyle.lineWidth;
        contextRef.current = ctx;
    };

    const canvasBackgroundSetting = (ctx: CanvasRenderingContext2D) => {
        if (!backgroundImage) return;

        const imageObj = new Image();
        imageObj.crossOrigin = 'anonymous';
        if (typeof backgroundImage === 'string') {
            imageObj.src = backgroundImage;
        } else {
            imageObj.src = URL.createObjectURL(backgroundImage);
        }

        imageObj.onload = () => {
            if (!canvasRef.current) return;
            const { clientWidth, clientHeight } = canvasRef.current;
            const imageAspectRatio = imageObj.width / imageObj.height;

            let newWidth, newHeight;
            if (clientWidth / clientHeight > imageAspectRatio) {
                newWidth = clientHeight * imageAspectRatio;
                newHeight = clientHeight;
            } else {
                newWidth = clientWidth;
                newHeight = clientWidth / imageAspectRatio;
            }
            ctx?.drawImage(
                imageObj,
                0,
                0,
                imageObj.width,
                imageObj.height,
                0,
                0,
                newWidth,
                newHeight
            );
        };
    };

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const erase = (offset: any) => {
        if (!contextRef.current) return;
        const { x, y } = offset;
        contextRef.current.globalCompositeOperation = 'destination-out';
        contextRef.current.beginPath();
        contextRef.current.arc(x, y, 15, 0, Math.PI * 2);
        contextRef.current.fill();
        contextRef.current.closePath();
        contextRef.current.globalCompositeOperation = 'source-over';

        erasePeerPaint && erasePeerPaint(offset);
    };

    const handlePointerDown = ({ nativeEvent }: PointerEvent) => {
        setIsDown(true);
        if (!contextRef.current) return;
        const { offsetX, offsetY } = nativeEvent;
        console.log(offsetX, offsetY);
        console.log(canvasRef.current?.width, canvasRef.current?.height);
        contextRef.current.beginPath();
        contextRef.current.moveTo(offsetX, offsetY);

        dataChannels?.map((channel: RTCDataChannel) => {
            try {
                channel.send(
                    JSON.stringify({
                        action: 'down',
                        offset: {
                            x: offsetX,
                            y: offsetY,
                            canvasWidth: canvasRef.current?.width,
                            canvasHeight: canvasRef.current?.height,
                        },
                    })
                );
            } catch (e) {
                console.log('전송 실패');
            }
        });
    };

    const handlePointerMove = ({ nativeEvent }: PointerEvent) => {
        if (!isDown || !contextRef.current) return;
        const { offsetX, offsetY } = nativeEvent;

        if (penStyle.isPen) {
            contextRef.current.strokeStyle = penStyle.strokeStyle;
            contextRef.current.lineWidth = penStyle.lineWidth;
            contextRef.current.lineTo(offsetX, offsetY);
            contextRef.current.stroke();
        } else {
            erase({ x: offsetX, y: offsetY });
        }

        dataChannels?.map((channel: RTCDataChannel) => {
            try {
                if (offsetX && offsetY) {
                    channel.send(
                        JSON.stringify({
                            action: 'move',
                            offset: {
                                x: offsetX,
                                y: offsetY,
                                canvasWidth: canvasRef.current?.width,
                                canvasHeight: canvasRef.current?.height,
                            },
                        })
                    );
                }
            } catch (e) {
                console.log('전송 실패');
            }
        });
    };

    const handlePointerUp = () => {
        setIsDown(false);
        if (!contextRef.current) return;
        contextRef.current.closePath();

        dataChannels?.map((channel: RTCDataChannel) => {
            try {
                channel.send(
                    JSON.stringify({
                        action: 'up',
                    })
                );
            } catch (e) {
                console.log('전송 실패');
            }
        });
    };

    const getCanvasDrawingImage = (size: { width: number; height: number }) => {
        const tempCanvas = document.createElement('canvas');
        tempCanvas.width = size.width as number;
        tempCanvas.height = size.height as number;
        const tempContext = tempCanvas.getContext('2d');
        if (!tempContext) {
            console.error('Failed to get 2D context from temporary canvas.');
            return;
        }
        if (!canvasRef.current) return;

        tempContext.drawImage(
            canvasRef.current,
            0,
            0,
            canvasRef.current.width,
            canvasRef.current.height,
            0,
            0,
            tempCanvas.width,
            tempCanvas.height
        );
        const image = canvasRef.current.toDataURL();
        const blob = dataURItoBlob(image as string);
        return blob;
    };

    const resetCanvas = () => {
        if (!canvasRef.current || !contextRef.current) return;
        contextRef.current.clearRect(0, 0, canvasRef.current.width, canvasRef.current.height);
    };

    return {
        handlePointerDown,
        handlePointerMove,
        handlePointerUp,
        erase,
        getCanvasDrawingImage,
        resetCanvas,
        penStyle,
    };
};

export default useMyPaint;

const dataURItoBlob = (dataURI: string) => {
    const byteString = atob(dataURI.split(',')[1]);
    const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const uint8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
        uint8Array[i] = byteString.charCodeAt(i);
    }
    return new Blob([arrayBuffer], { type: mimeString });
};
